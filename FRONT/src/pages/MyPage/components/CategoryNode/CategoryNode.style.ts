import styled from "styled-components";

export const CategoryNodeContainer = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--sub-color);
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 4px;
  flex-wrap: wrap;
`;

export const IconGroup = styled.div`
  display: flex;
  gap: 12px;
  visibility: hidden;
  opacity: 1;
  transition: opacity 0.2s ease-in-out, visibility 0.2s ease-in-out;

  ${CategoryNodeContainer}:hover & {
    visibility: visible;
    opacity: 1;
  }
`;

export const CategoryHeader = styled.div`
  display: flex;
  gap: 16px;
  align-items: center;

  svg {
    cursor: move;
    width: 24px;
  }

  svg:active {
    cursor: grabbing;
  }

  svg path {
    fill: var(--text-color);
  }
`;