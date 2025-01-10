import {
  TagInputContainer,
  TagContainer,
  Tag,
  TagText,
  RemoveButton,
  InputField,
  AddTagButton,
  AddTagButtonText,
  TagIcon,
} from "./TagWrapper.style";
import tagAddIcon from "@_assets/icons/tag-add-icon.svg";
import tagDeleteIcon from "@_assets/icons/tag-delete-icon.svg";

interface TagWrapperProps {
  tags: string[];
  newTag: string;
  onAddTagClick: () => void;
  onInputChange: (value: string) => void;
  onInputKeyDown: (key: string) => void;
  onRemoveTag: (index: number) => void;
  isInputVisible: boolean;
}

const TagWrapper: React.FC<TagWrapperProps> = ({
  tags,
  newTag,
  onAddTagClick,
  onInputChange,
  onInputKeyDown,
  onRemoveTag,
  isInputVisible,
}) => {
  return (
    <TagInputContainer>
      <AddTagButton onClick={onAddTagClick}>
        <AddTagButtonText>태그 추가</AddTagButtonText>
        <TagIcon src={tagAddIcon} />
      </AddTagButton>
      {isInputVisible && (
        <InputField
          type="text"
          value={newTag}
          onChange={(e) => onInputChange(e.target.value)}
          onKeyDown={(e) => onInputKeyDown(e.key)}
          placeholder="태그를 입력하세요"
        />
      )}

      <TagContainer>
        {tags.map((tag, index) => (
          <Tag key={index}>
            <TagText>{tag}</TagText>
            <RemoveButton
              onClick={() => onRemoveTag(index)}
              src={tagDeleteIcon}
            />
          </Tag>
        ))}
      </TagContainer>
    </TagInputContainer>
  );
};

export default TagWrapper;