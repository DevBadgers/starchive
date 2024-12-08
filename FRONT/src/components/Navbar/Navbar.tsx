import exampleProfile from "@_assets/images/example-profile.svg";
import toggleIcon from "@_assets/icons/toggle-icon.svg";
import { NavbarContainer, ToggleIcon, ProfileIcon } from "./Navbar.style";
import useAside from "../Aside/useAside";

function Navbar() {
  const { handleOpenAside } = useAside();

  return (
    <NavbarContainer>
      <ToggleIcon src={toggleIcon} alt="toggle" onClick={handleOpenAside} />
      <ProfileIcon src={exampleProfile} alt="profile" />
    </NavbarContainer>
  );
};

export default Navbar;