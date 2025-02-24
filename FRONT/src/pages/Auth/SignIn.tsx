import { AuthButton, AuthContainer, AuthForm, AuthHeader, AuthLinkContainer, AuthWrapper, Divider, Fieldset, Input, Label, LogoContainer, OAuthButton } from "./Auth.style";
import logoIcon from "@_assets/logo/logo.svg";
import GithubIcon from "@_assets/icons/github-icon.svg?react";
import { Link } from "react-router-dom";
import PasswordInput from "./components/PasswordInput";

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
          <Fieldset>
            <Label htmlFor="id">아이디</Label>
            <Input id="id" type="text" placeholder="아이디" />
          </Fieldset>
          <PasswordInput />
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