import Button from "../../../../components/Button/Button";
import { Category } from "../../../../types/category";
import useCategoryManagement from "../../hooks/useCategoryManagement";
import CategoryNode from "../CategoryNode/CategoryNode";
import { CategoryAddButton, CategoryList, CategoryManagementContainer, SubCategoryList } from "./CategoryManagement.style";

function CategoryManagement() {
  const {
    editingCategoryId,
    setEditingCategoryId,
  categories,
    newName,
    setNewName,
    createPrimaryCategory,
    createSubCategory,
    deleteCategory,
    updateCategoryName,
    moveCategory,
    handleSaveChanges
  } = useCategoryManagement();

  const commonCategoryProps = {
    editingCategoryId,
    setEditingCategoryId,
    newName,
    setNewName,
    createSubCategory,
    deleteCategory,
    updateCategoryName,
    onMove: moveCategory
  };

  return (
    <CategoryManagementContainer>
      {categories?.map((primaryCategory: Category) => (
        <CategoryList key={primaryCategory.id}>
          <CategoryNode
            name={primaryCategory.name}
            id={primaryCategory.id || 0}
            {...commonCategoryProps}
          />
          {primaryCategory.children?.map((subCategory) => (
            <SubCategoryList key={subCategory.id}>
              <CategoryNode
                name={subCategory.name}
                id={subCategory.id || 0}
                parentId={primaryCategory.id}
                {...commonCategoryProps}
              />
            </SubCategoryList>
          ))}
        </CategoryList>
      ))}
      <CategoryAddButton onClick={createPrimaryCategory}>
        <h4>카테고리 추가</h4>
      </CategoryAddButton>
      <Button content="변경사항 저장" type="Primary" handleButtonClick={handleSaveChanges} />
    </CategoryManagementContainer>
  )
}

export default CategoryManagement;