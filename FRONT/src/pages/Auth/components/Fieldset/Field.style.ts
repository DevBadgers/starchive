import styled from "styled-components";

export const FieldContainer = styled.div`
  border: none;
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin: 0;
  padding: 0;
`;

export const Label = styled.label`
  padding-left: 4px;
  color: #757575;
`;

export const PasswordInputContainer = styled.div`
  position: relative;
  align-content: center;
`;

export const Input = styled.input`
  align-content: center;
  width: 100%;
  padding: 16px 24px;
  border-radius: 8px;
  border: 1px solid var(--primary-color);
  font-size: 16px;

  &[type="password"] {
    padding-right: 49px;
  }
`;

export const PasswordVisibilityToggleButton = styled.button`
  all: unset;
  position: absolute;
  display: block;
  width: 17px;
  height: 14px;
  right: 24px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
`;

export const Guidance = styled.div`
  padding-left: 4px;
  color: var(--point-color);
  overflow-wrap: break-word;
`;