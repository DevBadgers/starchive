import { TagList, Tag } from "../TagWrapper/TagWrapper.style";

interface TagProps {
  tagList: string[];
}

function TagWrapper({ tagList }: TagProps) {
  return (
    <TagList>
      {tagList.map((tag, index) => (
        <Tag key={index}>{tag}</Tag>
      ))}
    </TagList>
  );
}

export default TagWrapper;
