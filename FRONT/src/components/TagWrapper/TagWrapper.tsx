import { Tag as TagType } from "../../types/tag";
import { TagList, Tag } from "./TagWrapper.style";

interface TagWrapperProps {
  tagList: TagType[];
  onTagClick?: (name: string) => void;
  selectedTag?: string | null;
}

function TagWrapper({ tagList, onTagClick, selectedTag }: TagWrapperProps) {
  return (
    <TagList>
      {tagList.map((tag, index) => (
        <Tag
          key={index}
          onClick={() => onTagClick && onTagClick(tag.name)}
          $isSelected={tag.name === selectedTag}
        >
          {tag.name}
        </Tag>
      ))}
    </TagList>
  );
}

export default TagWrapper;