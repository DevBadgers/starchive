import styled from "styled-components";

export const MarkdownContainer = styled.div`
  blockquote { // 인용문
    margin: 0;
    padding: 8px 16px;
    border-left: 4px solid var(--point-color);
    background-color: var(--background-color);
  }

  ul, ol {
    display: inline-block;
    margin: 0;
    padding-left: 20px;
  }

  ul ul, ol ol {
    line-height: 1;
  }

  hr {
    background-color: var(--line-color);
    height: 1px;
    border: 0;
  }
`;

export const InlineCode = styled.code`
  display: inline;
  background-color: var(--background-color);
  color: var(--point-color);
  padding: 2px 5px;
  border-radius: 5px;
`;

export const PreviewImg = styled.img`
  max-width: 600px;
  height: auto;
  object-fit: contain;
  display: block;
  margin: 0 auto;
`;