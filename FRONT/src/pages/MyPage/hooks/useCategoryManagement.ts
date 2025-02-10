import { useEffect, useState } from "react";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import {
  fetchCategories,
  createCategory as apiCreateCategory,
  updateCategory as apiUpdateCategory,
  deleteCategory as apiDeleteCategory,
} from "@_services/categoryApi";
import { ApiResponse } from "../../../services/api";
import { Category } from "../../../types/category";

interface Payload {
  categoryId?: number,
  name?: string,
  parentId?: number | null
}

interface PendingChange {
  type: "create" | "update" | "delete";
  payload: Payload
}

function useCategoryManagement() {
  const queryClient = useQueryClient();
  const [categories, setCategories] = useState<Category[]>([]);
  const [pendingChanges, setPendingChanges] = useState<PendingChange[]>([]);
  const [newName, setNewName] = useState<string>('새 카테고리');
  const [editingCategoryId, setEditingCategoryId] = useState<number | null>(null);
  const { data: categoryData } = useQuery<ApiResponse<Category[]>>({
    queryKey: ["categories"],
    queryFn: fetchCategories,
  });

  useEffect(() => {
    if (categoryData?.data) {
      setCategories(categoryData?.data);
    }
  }, [categoryData]);

  const addPendingChange = (change: PendingChange) => {
    setPendingChanges((prev) => [...prev, change]);
  };

  const applyPendingChanges = async () => {
    for (const change of pendingChanges) {
      const { type, payload } = change;
      const { categoryId, name, parentId } = payload;
      if (type === "create") await apiCreateCategory(name!, parentId!);
      else if (type === "update") await apiUpdateCategory(categoryId!, name!, parentId!);
      else if (type === "delete") await apiDeleteCategory(categoryId!);
    }
    setPendingChanges([]);
    queryClient.invalidateQueries({ queryKey: ["categories"] });
  };

  const handleSaveChanges = () => {
    if (pendingChanges.length && confirm("변경사항을 저장하시겠습니까?")) {
      applyPendingChanges();
    }
  }

  const createCategory = (parentId: number | null) => {
    const name = "새 카테고리";
    const tempId = Date.now();
    const newCategory: Category = { id: tempId, name, children: [] };
    const payload: Payload= { categoryId: tempId, name, parentId };

    setCategories((prev) => {
      if (parentId === null) return [...prev, newCategory];
      return prev.map((category) =>
        category.id === parentId ? { ...category, children: [...category.children, newCategory] } : category
      );
    });
    console.log('parentId', parentId);
    addPendingChange({ type: 'create', payload });
  };

  const createPrimaryCategory = () => {
    createCategory(null);
  }

  const createSubCategory = (parentId: number) => {
    createCategory(parentId);
  }

  const updateCategoryName = (categoryId: number) => {
    setEditingCategoryId(categoryId);
    if (!editingCategoryId) {
      const payload: Payload = { categoryId, name: newName };
      setPendingChanges((prev) => [...prev, { type: "update", payload }]); // payload 사용
      setCategories((prev) =>
        prev.map((category) =>
          category.id === categoryId
            ? { ...category, name: newName  }
            : {
                ...category,
                children: category.children.map((child) => (child.id === categoryId ? { ...child, name: newName  } : child)),
              }
        )
      );
    }
  };

  const deleteCategory = (categoryId: number) => {
    addPendingChange({ type: "delete", payload: { categoryId } });
    setCategories((prev) =>
      prev.filter((category) => category.id !== categoryId).map((category) => ({
        ...category,
        children: category.children.filter((child) => child.id !== categoryId),
      }))
    );
  }

  const moveCategory = (sourceId: number, parentId: number) => {
    const findAndRemoveCategory = (categories: Category[], id: number): { removed: Category | null; updatedCategories: Category[] } => {
      for (const category of categories) {
        if (category.id === id) {
          return { removed: category, updatedCategories: categories.filter((c) => c.id !== id) };
        }
        const result = findAndRemoveCategory(category.children || [], id);
        if (result.removed) {
          category.children = result.updatedCategories; // Update children after removal
          return { removed: result.removed, updatedCategories: categories };
        }
      }
      return { removed: null, updatedCategories: categories };
    };

    const categoriesCopy = [...categories];
    const { removed: sourceCategory, updatedCategories } = findAndRemoveCategory(categoriesCopy, sourceId);

    if (!sourceCategory) {
      return;
    }

    const addCategory = (categories: Category[], parentId: number, categoryToAdd: Category): boolean => {
      for (const category of categories) {
        if (category.id === parentId) {
          category.children = [...(category.children || []), categoryToAdd];
          return true; // 없애도
        }
        if (addCategory(category.children || [], parentId, categoryToAdd)) {
          return true;
        }
      }
      return false;
    };

    if (!addCategory(updatedCategories, parentId, sourceCategory)) { // 없애도
      console.error("Destination category not found");
      return;
    }

    setCategories(updatedCategories);
    addPendingChange({ type: "update", payload: { categoryId: sourceId, parentId } });
  };

  return {
    categories,
    createPrimaryCategory,
    createSubCategory,
    deleteCategory,
    updateCategoryName,
    handleSaveChanges,
    editingCategoryId,
    setEditingCategoryId,
    newName,
    setNewName,
    moveCategory,
  };
}

export default useCategoryManagement;