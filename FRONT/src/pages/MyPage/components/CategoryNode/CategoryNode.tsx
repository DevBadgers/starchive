import { CategoryHeader, CategoryNodeContainer, IconGroup } from "./CategoryNode.style";
import CategoryMoveIcon from "@_assets/icons/toggle-icon.svg?react"
import EditIcon from "@_assets/icons/edit-icon.svg?react"
import TrashIcon from "@_assets/icons/trash-icon.svg?react"
import AddIcon from "@_assets/icons/add-icon.svg?react"
import IconButton from "@_components/IconButton/IconButton";

interface CategoryNodeProps {
  name: string,
  id: number,
  parentId?: number,
  editingCategoryId: number | null,
  setEditingCategoryId: (editingCategoryId: number | null) => void,
  newName: string,
  setNewName: (newName: string) => void,
  onMove: (sourceId: number, destinationId: number) => void,
  createSubCategory: (parentId: number) => void,
  deleteCategory: (id: number) => void,
  updateCategoryName: (id: number) => void,
}

function CategoryNode({
  name,
  id,
  parentId,
  editingCategoryId,
  setEditingCategoryId,
  newName,
  setNewName,
  onMove,
  createSubCategory,
  updateCategoryName,
  deleteCategory
}: CategoryNodeProps) {
  const handleDragStart = (e: React.DragEvent) => {
    e.dataTransfer.setData("categoryId", id.toString());
  };

  const handleDrop = (e: React.DragEvent) => {
    const sourceId = Number(e.dataTransfer.getData("categoryId"));
    onMove(sourceId, id);
  };

  return (
    <CategoryNodeContainer
      draggable
      onDragStart={handleDragStart}
      onDragOver={(e) => e.preventDefault()}
      onDrop={handleDrop}
    >
      <CategoryHeader>
        <CategoryMoveIcon aria-label='drag to move the category' />
        { id === editingCategoryId ? (
          <input
            value={newName}
            onChange={(e) => setNewName(e.target.value)}
            onBlur={() => setEditingCategoryId(editingCategoryId)}
            autoFocus
          />
        ) : (
          <h4>{ name }</h4>
        )}
      </CategoryHeader>
      { id !== 0 &&
        <IconGroup>
          { !parentId &&
            <IconButton tooltip='추가' onClick={() => createSubCategory(parentId as number)}><AddIcon /></IconButton>
          }
          <IconButton tooltip='수정' onClick={() => updateCategoryName(id)}><EditIcon /></IconButton>
          <IconButton tooltip='삭제' onClick={() => deleteCategory(id)}><TrashIcon /></IconButton>
        </IconGroup>
      }
    </CategoryNodeContainer>
  )
}

export default CategoryNode;