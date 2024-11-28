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

    @media (min-width: 768px) {
        display: none;
    }
`;

export const ProfileIcon = styled.img`
    width: 35px;
    height: 35px;
    border-radius: 50%;
    cursor: pointer;
`;

export const ImageWrapper = styled.div`
    display: flex;
    align-items: center;
    gap: 5px;

    h4 {
        color: #fff;
    }

    img {
        width: 24px;
        height: 24px;
    }
`;

export const MenuWrapper = styled.article`
    display: flex;
    align-items: center;
    gap: 30px;
    
    h4 {
        color: #fff;
        cursor: pointer;
    }
    
    h4:hover {
        color: var(--point-color);
    }
    ${ImageWrapper} h4:hover {
        color: #fff;
    }
    
    @media (max-width: 768px) {
        display: none;
    }
}`;