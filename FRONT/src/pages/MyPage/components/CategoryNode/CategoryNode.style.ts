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

  &:hover {
    transform: scale(1.03);
    transition: transform 0.2s ease;
  }
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

export const Input = styled.input`
  all: unset;
  font-size: 16px;
  font-weight: 500;
`;