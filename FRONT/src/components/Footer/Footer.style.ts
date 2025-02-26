import styled from "styled-components";

export const FooterContainer = styled.footer`
  width: 100%;
  bottom: 0;
  background-color: var(--primary-color);
  padding: 30px 20px;
  color: var(--footer-text-color);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;

  @media (max-width: 470px) {
    align-items: flex-start;
  }
`;

export const InfoContainer = styled.section`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  align-items: center;

  @media (max-width: 470px) {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
`;

export const LeftContainer = styled.article`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export const LogoWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 3px;
  color: #fff;

  img {
    width: 20px;
    height: 20px;
  }

  h4 {
    font-weight: 700;
    margin: 0;
  }
`;

export const TextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3px;
  font-size: 12px;
  margin-left: 23px;

  @media (max-width: 470px) {
    margin-left: 0;
  }
`;

export const CopyRight = styled.p`
  font-size: 11px;
  margin: 0;
`;

export const RightContainer = styled.article`
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: flex-end;

  img {
    width: 28px;
    height: 28px;
    cursor: pointer;
  }

  @media (max-width: 470px) {
    flex-direction: row;
    align-items: center;
  }
`;

export const FooterLinkList = styled.ul`
  display: flex;
  flex-direction: row;
  list-style: none;
  gap: 10px;
  margin: 0;
  padding: 0;
`;

export const FooterLink = styled.li`
  font-size: 13px;
  cursor: pointer;
  &:hover {
    text-decoration: underline;
  }
  & > a {
    text-decoration: none;
    color: var(--footer-text-color);
  }
`;

export const Separator = styled.hr`
  width: 1px;
  height: 15px;
  background-color: var(--footer-text-color);
  border: none;
  margin: 0;
`;
