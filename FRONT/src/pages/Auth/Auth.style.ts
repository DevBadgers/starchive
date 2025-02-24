import styled from "styled-components";

export const AuthWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 104px 0;
  min-height: calc(100dvh - 55px);

  @media (max-width: 470px) {
    padding: 0;
  }
`;

export const AuthContainer = styled.section`
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 40px 80px;
  width: 500px;
  background-color: white;
  border-radius: 32px;
  box-shadow: 0px 8px 30px 5px rgba(0, 0, 0, 0.1);

  @media (max-width: 470px) {
    padding: 64px 32px;
    background-color: inherit;
    box-shadow: none;
  }
`;

export const AuthHeader = styled.header`
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding-bottom: 8px;
`;

export const LogoContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;

  img {
    width: 16px;
  }
`;

export const AuthForm = styled.form`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const Fieldset = styled.fieldset`
  border: none;
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin: 0;
  padding: 0;
`;

export const Label = styled.label`
  padding-left: 4px;
`;

export const PasswordInputContainer = styled.div`
  position: relative;
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

export const EyeIcon = styled.img`
  position: absolute;
  width: 17px;
  right: 24px;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
  cursor: pointer;
`;

const authButtonStyle = `
  all: unset;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 500;
`;

export const AuthButton = styled.button`
  ${authButtonStyle};
  padding: 16px 24px;
  background-color: var(--primary-color);
  margin-top: 16px;
`;

export const Divider = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;

  hr {
    flex-grow: 1;
    border: none;
    height: 1px;
    background: var(--line-color);
  }

  span {
    color: var(--footer-text-color);
  }
`;

export const OAuthButton = styled.button`
  ${authButtonStyle};
  padding: 14px 24px;
  background-color: #24292F;
  gap: 16px;

  svg path {
    fill: white;
  }
`;

export const AuthLinkContainer = styled.nav`
  display: flex;
  justify-content: center;
  color: var(--primary-color);

  :visited {
    color: var(--primary-color);
  }
`;
