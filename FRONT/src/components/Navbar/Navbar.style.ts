import { Link } from 'react-router-dom';
import styled from 'styled-components';

export const NavbarContainer = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: var(--primary-color);
`;

export const ToggleIcon = styled.img`
  width: 24px;
  height: 24px;
  cursor: pointer;
`;

export const ProfileIcon = styled.img`
  width: 35px;
  height: 35px;
  border-radius: 50%;
  cursor: pointer;
`;

export const SignInLinkWrapper = styled.div`
  height: 35px;
  align-content: center;
`;

export const SignInLink = styled(Link)`
  all: unset;
  display: block;
  color: white;
  padding: 4px 8px 2px 8px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;

  &:hover,
  &:active {
    background-color: var(--sub-color);
    color: var(--text-color);
  }
`;