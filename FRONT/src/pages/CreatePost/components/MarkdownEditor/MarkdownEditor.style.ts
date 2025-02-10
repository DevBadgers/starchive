import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 500px;
  background-color: #fff;
`;

const sharedStyles = `
  padding: 24px;
  border: none;
  word-wrap: break-word;
  white-space: pre-wrap;
  border-top: 1px solid var(--line-color);
`;

export const Editor = styled.textarea`
  ${sharedStyles}
  box-sizing: border-box;
  width: 100%;
  flex-grow: 1;
  font-family: inherit;
  resize: none;
  line-height: 1.7;

  &::placeholder {
    white-space: pre-wrap;
  }

  &:focus {
    outline: none;
  }
`;

export const Preview = styled.div`
  ${sharedStyles}
`;