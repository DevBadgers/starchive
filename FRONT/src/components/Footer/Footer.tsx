import {
  FooterContainer,
  InfoContainer,
  LeftContainer,
  RightContainer,
  CopyRight,
  LogoWrapper,
  TextWrapper,
  FooterLink,
  FooterLinkList,
  Separator,
} from "./Footer.style";
import { Link } from "react-router-dom";
import logoIcon from "@_assets/logo/logo.svg";
import githubIcon from "@_assets/icons/github-icon.svg";

function Footer() {
  return (
    <FooterContainer>
      <InfoContainer>
        <LeftContainer>
          <LogoWrapper>
            <img src={logoIcon} alt="DevBadgers Logo" />
            <h4>Starchive</h4>
          </LogoWrapper>
          <TextWrapper>
            <span>Powered by <strong>DevBadgers</strong></span>
            <span>010 - 2510 - 6762</span>
            <span>devbadgers@gmail.com</span>
          </TextWrapper>
        </LeftContainer>
        <RightContainer>
          <FooterLinkList>
            <FooterLink><Link to="/">홈</Link></FooterLink>
            <Separator />
            <FooterLink><Link to="/">이용약관</Link></FooterLink>
            <Separator />
            <FooterLink><Link to="/">문의하기</Link></FooterLink>
            <Separator />
            <FooterLink><Link to="/">로그아웃</Link></FooterLink>
          </FooterLinkList>
          <img src={githubIcon} alt="github-icon" />
        </RightContainer>
      </InfoContainer>
      <CopyRight>ⓒ Copyright 2024 - DevBadgers. All rights reserved.</CopyRight>
    </FooterContainer>
  );
}

export default Footer;