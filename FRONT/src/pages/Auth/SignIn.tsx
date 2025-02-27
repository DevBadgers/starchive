import { AuthButton, AuthContainer, AuthForm, AuthHeader, AuthLinkContainer, AuthWrapper, Divider, LogoContainer, OAuthButton } from "./Auth.style";
import logoIcon from "@_assets/logo/logo.svg";
import GithubIcon from "@_assets/icons/github-icon.svg?react";
import { Link } from "react-router-dom";
import Field from "./components/Fieldset/Field";

function SignIn() {
  return (
    <AuthWrapper>
      <AuthContainer>
        <AuthHeader>
          <LogoContainer>
            <img src={logoIcon} alt='starchive logo' /><h4>Starchive</h4>
          </LogoContainer>
          <h1>로그인</h1>
        </AuthHeader>
        <AuthForm>
          <Field id="id" label="아이디" placeholder="아이디" type="text" />
          <Field id="password" label="비밀번호" placeholder="비밀번호" type="password" />
          <AuthButton type="submit">로그인</AuthButton>
        </AuthForm>
        <Divider><hr /><span>OR</span><hr /></Divider>
        <OAuthButton><GithubIcon />Sign in with Github</OAuthButton>
        <AuthLinkContainer><Link to="/signup">회원가입</Link></AuthLinkContainer>
      </AuthContainer>
    </AuthWrapper>
  )
}

export default SignIn;