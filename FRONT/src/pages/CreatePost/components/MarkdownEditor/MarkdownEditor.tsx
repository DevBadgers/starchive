import { useState } from "react";
import { Container, Editor, Preview } from "./MarkdownEditor.style";
import ToolBar from "../ToolBar/ToolBar";
import MarkdownRenderer from "@_components/MarkdownRenderer/MarkdownRenderer";

interface MarkdownEditorProps {
  markdown: string,
  textareaRef: React.RefObject<HTMLTextAreaElement>,
  fileInputRef: React.RefObject<HTMLInputElement>,
  onChange: (value: string) => void,
  onIconButtonClick: (action: string) => void,
  onFileUpload: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

function MarkdownEditor({
  markdown,
  onChange,
  onIconButtonClick,
  textareaRef,
  fileInputRef,
  onFileUpload,
}: MarkdownEditorProps) {

  const markdownGrammar = `# 제목\n## 부제목\n*기울임* _기울임_\n**굵게** __굵게__\n[링크](https://www.google.com)\n![이미지](이미지 주소)\n> 인용문\n* 순서가 없는 목록\n- 순서가 없는 목록\n1. 순서가 있는 목록\n1) 순서가 있는 목록\n띄어쓰기 3번 하고 목록 => 하위목록\n--- 수평선 *** 수평선\n\`한 줄 코드\`\n\`\`\`\n코드블럭\n\`\`\`\n여러 줄의 공백\n&nbsp;\n&nbsp;\n`;
  const [viewMode, setViewMode] = useState<'write' | 'preview'>('write');

  return (
    <Container>
      <ToolBar
        viewMode={viewMode}
        setViewMode={setViewMode}
        onIconButtonClick={onIconButtonClick}
        handleAddPhotoClick={() => fileInputRef.current?.click()}
      />
      {viewMode === "write" ? (
        <Editor
          ref={textareaRef}
          value={markdown}
          onChange={(e) => onChange(e.target.value)}
          placeholder={markdownGrammar}
        />
      ) : (
        <Preview><MarkdownRenderer markdown={markdown} /></Preview>
      )}
      <input
        type="file"
        accept="image/*"
        style={{ display: "none" }}
        ref={fileInputRef}
        onChange={onFileUpload}
      />
    </Container>
  );
}

export default MarkdownEditor;