import styled from "styled-components";

export const CategoryManagementContainer = styled.div`
  max-width: 400px;
  padding: 20px 30px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

export const CategoryList = styled.div`
  width: 100%;
`;

export const SubCategoryList = styled.div`
  padding-left: 52px;
`;

export const CategoryAddButton = styled.button`
  width: 100%;
  border: 1px solid var(--primary-color);
  color: var(--primary-color);
  align-items: center;
  background-color: #fff;
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 16px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;

  &:hover {
    background-color: var(--primary-color);
    color: white;
  }

  &:active {
    background-color: var(--primary-color);
    color: white;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 4px 10px;
  }
`;