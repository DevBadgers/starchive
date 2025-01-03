import { useState } from "react";
import { Container, IconGroup, Icon, Editor, EditorPreviewWrapper, InlineCode, Preview } from "./EditorContainer.style";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { docco } from "react-syntax-highlighter/dist/esm/styles/hljs";
import alignLeftIcon from '@_assets/icons/align-left.svg';
import alignCenterIcon from '@_assets/icons/align-center.svg';
import alignRightIcon from '@_assets/icons/align-right.svg';
import addPhotoIcon from '@_assets/icons/add-photo.svg';
import { CodeProps } from "react-markdown/lib/ast-to-react";
import useCreatePost from "../../hooks/useCreatePost";

function EditorContainer() {
  const markdownGrammar = `# 제목\n## 부제목\n*기울임* _기울임_\n**굵게** __굵게__\n[링크](https://www.google.com)\n![이미지](이미지 주소)\n> 인용문\n* 순서가 없는 목록\n- 순서가 없는 목록\n1. 순서가 있는 목록\n1) 순서가 있는 목록\n띄어쓰기 2번 하고 목록 => 하위목록\n--- 수평선 *** 수평선\n\`한 줄 코드\`\n\`\`\`\n코드블럭\n\`\`\`\n여러 줄의 공백\n&nbsp;\n&nbsp;\n`;
  const [alignment, setAlignment] = useState('left');
  const { post, handlePostChange } = useCreatePost();

  const renderCodeBlock = ({ inline, className, children, ...rest }: CodeProps) => {
    if (inline) {
      return <InlineCode>{String(children).replace(/\n$/, '')}</InlineCode>;
    }

    const language = /language-(\w+)/.exec(className || '')?.[1] || 'plaintext';
    return (
      <SyntaxHighlighter
        {...rest}
        style={docco}
        language={language}
        children={String(children).replace(/\n$/, '')}
      />
    )
  }

  return (
    <Container>
      <IconGroup>
        <Icon src={addPhotoIcon} alt="사진 추가" />
        <Icon src={alignLeftIcon} alt="왼쪽 정렬" onClick={() => setAlignment('left')} />
        <Icon src={alignCenterIcon} alt="가운데 정렬" onClick={() => setAlignment('center')} />
        <Icon src={alignRightIcon} alt="오른쪽 정렬" onClick={() => setAlignment('right')}/>
      </IconGroup>
      <EditorPreviewWrapper>
        <Editor
          value={post.content}
          onChange={(e) => handlePostChange('content', e.target.value)}
          placeholder={markdownGrammar}
          required
        />
        <Preview alignment={alignment}>
          <ReactMarkdown
            children={post.content}
            remarkPlugins={[remarkGfm]}
            components={{code: renderCodeBlock}}
          />
        </Preview>
      </EditorPreviewWrapper>
    </Container>
  );
}

export default EditorContainer;