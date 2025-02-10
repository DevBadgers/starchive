import styled from "styled-components";

export const CategorySelectorContainer = styled.div`
  position: relative;
  margin-left: auto;
  width: 212px;
`;

export const CategorySelect = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 20px;
  border: 1px solid #000;
  border-radius: 15px;
  cursor: pointer;
  background-color: #fff;
  font-size: 16px;
  width: 100%;
  box-sizing: border-box;
`;

export const DropdownList = styled.ul`
  position: absolute;
  top: 35px;
  height: auto;
  left: 0;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #000;
  background-color: #fff;
  z-index: 100;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 5px;
`;

export const CategoryItem = styled.li`
  list-style: none;
  padding: 0;
  background-color: #fff;
  font-size: 16px;
`;

export const BigCategory = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 5px;
  padding: 5px 0;

  img {
    width: 20px;
    height: 20px;
  }

  &:hover {
    background-color: var(--hover-background-color);
  }
`;

export const SubCategoryList = styled.ul`
  list-style: none;
  padding: 0;
`;

export const SubCategoryItem = styled.li`
  font-size: 16px;
  cursor: pointer;
  margin: 0 0 0 25px;
  padding: 5px 0;

  &:hover {
    background-color: var(--hover-background-color);
  }
`;