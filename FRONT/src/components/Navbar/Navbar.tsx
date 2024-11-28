import exampleProfile from "../../assets/images/example-profile.svg";
import toggleIcon from "../../assets/icons/toggle-icon.svg";
import logoIcon from "../../assets/logo/logo.svg";
import { NavbarContainer, ToggleIcon, ProfileIcon, ImageWrapper, MenuWrapper } from "./Navbar.style";

function Navbar() {
  return (
    <NavbarContainer>
      <ToggleIcon src={toggleIcon} alt="toggle" />
      <MenuWrapper>
        <ImageWrapper>
          <img src={logoIcon} alt="toggle" />
          <h4><strong>Starchive</strong></h4>
        </ImageWrapper>
        <h4>메뉴 1</h4>
        <h4>메뉴 2</h4>
        <h4>메뉴 3</h4>
      </MenuWrapper>
      <ProfileIcon src={exampleProfile} alt="profile" />
    </NavbarContainer>
  );
};

export default Navbar;