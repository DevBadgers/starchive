import { styled } from "styled-components";

export const PostContainer = styled.section`
  display: flex;
  flex-direction: column;
  padding: 30px 20pxs;
  margin: auto;
  gap: 20px;
  max-width: 1080px;
`;

export const MainContent = styled.article`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 10px;
`;
