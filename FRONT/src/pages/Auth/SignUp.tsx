import { AuthButton, AuthContainer, AuthForm, AuthHeader, AuthLinkContainer, AuthWrapper, EditButton, LogoContainer, ProfileImage, ProfileImageContainer, ProfileImageWrapper } from "./Auth.style";
import { Link } from "react-router-dom";
import logoIcon from "@_assets/logo/logo.svg";
import editIcon from "@_assets/icons/profile-edit-icon.svg"
import Field from "./components/Fieldset/Field";

function SignUp() {
  return (
    <AuthWrapper>
      <AuthContainer>
        <AuthHeader>
          <LogoContainer>
            <img src={logoIcon} alt='starchive logo' /><h4>Starchive</h4>
          </LogoContainer>
          <h1>회원가입</h1>
        </AuthHeader>
        <AuthForm>
          <ProfileImageWrapper>
            <ProfileImageContainer>
              <ProfileImage />
              <EditButton>
                <img src={editIcon} alt="프로필 이미지 수정" />
              </EditButton>
            </ProfileImageContainer>
          </ProfileImageWrapper>
          <Field id="nickname" label="닉네임" placeholder="닉네임" type="text" />
          <Field id="id" label="아이디" placeholder="아이디" type="text" />
          <Field id="password" label="비밀번호" placeholder="비밀번호" type="password" showGuidance />
          <Field id="bio" label="프로필 한 마디" placeholder="간단한 자기소개를 입력하세요" type="text" />
          <AuthButton type="submit">회원가입</AuthButton>
        </AuthForm>
        <AuthLinkContainer><Link to="/signin">로그인</Link></AuthLinkContainer>
      </AuthContainer>
    </AuthWrapper>
  )
}

export default SignUp;