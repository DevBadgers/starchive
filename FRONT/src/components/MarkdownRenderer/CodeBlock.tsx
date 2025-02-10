import { useEffect, useRef } from "react";
import { InlineCode } from "./MarkdownRenderer.style";
import Prism from "prismjs";
import 'prismjs/themes/prism.css';
import 'prismjs/components/prism-javascript';
import 'prismjs/components/prism-typescript';
import 'prismjs/components/prism-java';
import 'prismjs/components/prism-python';
import 'prismjs/components/prism-c';
import 'prismjs/components/prism-cpp';
import 'prismjs/components/prism-css';
import 'prismjs/components/prism-sql';
import 'prismjs/components/prism-bash';
import 'prismjs/components/prism-yaml';
import 'prismjs/components/prism-json';

const CodeBlock = ({ inline, className, children }: { inline?: boolean; className?: string; children: React.ReactNode }) => {
  const codeRef = useRef<HTMLElement>(null);

  useEffect(() => {
    if (codeRef.current) {
      Prism.highlightElement(codeRef.current); // 개별 코드 블록 하이라이트
    }
  }, [children]);

  if (inline) {
    return <InlineCode>{ children }</InlineCode>;
  }

  const language = className?.replace("language-", "") || "plaintext";
  const codeString = String(children).replace(/\n$/, "");

  return (
    <pre className={className}>
      <code ref={codeRef} className={`language-${language}`}>{ codeString }</code>
    </pre>
  );
};

export default CodeBlock;
