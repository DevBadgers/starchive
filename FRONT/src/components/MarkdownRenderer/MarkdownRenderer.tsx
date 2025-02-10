import Markdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { MarkdownContainer, PreviewImg } from "./MarkdownRenderer.style";
import CodeBlock from "./CodeBlock";

const MarkdownRenderer = ({ markdown }: { markdown: string }) => {
  return (
    <MarkdownContainer>
      <Markdown
        children={markdown}
        remarkPlugins={[remarkGfm]}
        components={{
          code: CodeBlock,
          img: ({ src, alt }) => <PreviewImg src={src} alt={alt} />,
        }}
      />
    </MarkdownContainer>
  );
};

export default MarkdownRenderer;
