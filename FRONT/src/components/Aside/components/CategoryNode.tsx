import categoryToggleButton from '@_assets/icons/category-toggle-button.svg'
import useCategoryNode from './useCategoryNode';
import { Category, CategoryId } from '../../../types/category';
import {
  CategoryButton,
  CategoryToggleButton,
  SubCategoryGroup,
  TopCategoryTitle,
  SubCategoryTitle
} from './CategoryNode.style';

interface CategoryNodeProps {
  category: Category;
  activeCategoryId?: CategoryId;
  onSelect: (id: CategoryId) => void;
}

function CategoryNode({ category, onSelect, activeCategoryId }: CategoryNodeProps) {
  const { isOpen, handleToggle } = useCategoryNode();
  const hasChildren = category.children && category.children.length > 0;

  return (
    <li>
      <CategoryButton onClick={() => onSelect(category.id)}>
        {hasChildren && (
          <CategoryToggleButton
            src={categoryToggleButton}
            alt="toggle"
            $isOpen={isOpen}
            onClick={handleToggle}
          />
        )}
        <TopCategoryTitle $isActive={category.id === activeCategoryId}>{category.name}</TopCategoryTitle>
      </CategoryButton>
      {hasChildren && isOpen && (
        <SubCategoryGroup>
          {category.children.map((childCategory: Category) => (
            <CategoryButton key={childCategory.id} onClick={() => onSelect(childCategory.id)}>
              <SubCategoryTitle $isActive={childCategory.id === activeCategoryId}>{childCategory.name}</SubCategoryTitle>
            </CategoryButton>
          ))}
        </SubCategoryGroup>
      )}
    </li>
  );
}

export default CategoryNode;