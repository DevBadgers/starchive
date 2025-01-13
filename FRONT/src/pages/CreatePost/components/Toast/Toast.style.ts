import styled, { keyframes } from "styled-components";

const scaleUp = keyframes`
  0% {
    transform: translateX(-50%) scale(0.5);
    opacity: 0;
  }
  100% {
    transform: translateX(-50%) scale(1);
    opacity: 1;
  }
`

const scaleDown = keyframes`
  0% {
    transform: translateX(-50%) scale(1);
    opacity: 1;
  }
  100% {
    transform: translateX(-50%) scale(0.5);
    opacity: 0;
  }
`

export const ToastWrapper = styled.div<{ $isVisible: boolean }>`
  position: fixed;
  top: 16px;
  left: 50%;
  background-color: var(--accent-color);
  padding: 12px 16px;
  border-radius: 5px;
  cursor: pointer;
  z-index: 1000;
  box-shadow: rgba(0, 0, 0, 0.15) 0px 4px 10px;
  animation: ${({ $isVisible }) =>
    $isVisible ? scaleUp : scaleDown} 0.3s ease forwards;
`

export const Message = styled.p`
  font-weight: 500;
`;