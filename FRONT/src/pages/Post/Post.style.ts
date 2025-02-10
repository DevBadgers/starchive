import { styled } from "styled-components";
import { HashTagContainer } from "../Home/components/PostItem/PostItem.style";

export const PostContainer = styled.section`
  display: flex;
  flex-direction: column;
  padding: 30px 20px;
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

export const Line = styled.div`
  width: 100%;
  height: 1px;
  background-color: #DEE1E1;
  margin: 15px 0;
`;

export const Content = styled.div`
  padding: 40px 40px 80px 40px;
  min-height: calc(100dvh - 640px);
`
export const PostHashTagContainer = styled(HashTagContainer)`
  margin-top: 20px;
`;