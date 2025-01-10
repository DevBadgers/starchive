import { useState } from 'react';

function useTag(initialTags: string[] = []) {
  const [tags, setTags] = useState<string[]>(initialTags);
  const [newTag, setNewTag] = useState<string>('');
  const [isInputVisible, setIsInputVisible] = useState<boolean>(false);

  const handleAddTagClick = () => {
    setIsInputVisible(true);
  };

  const handleInputChange = (value: string) => {
    setNewTag(value);
  };

  const handleInputKeyDown = (key: string) => {
    if (key === 'Enter' && newTag.trim()) {
      setTags([...tags, newTag.trim()]);
      setNewTag('');
      setIsInputVisible(false);
    }
  };

  const handleRemoveTag = (index: number) => {
    setTags(tags.filter((_, i) => i !== index));
  };

  return {
    tags,
    newTag,
    isInputVisible,
    handleAddTagClick,
    handleInputChange,
    handleInputKeyDown,
    handleRemoveTag,
  };
}

export default useTag;