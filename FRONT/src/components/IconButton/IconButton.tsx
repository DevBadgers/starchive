import { IconButtonContainer, Tooltip } from "./IconButton.style";

interface IconButtonProps {
  children: React.ReactNode;
  onClick: () => void;
  tooltip?: string;
}

function IconButton({ children, onClick, tooltip }: IconButtonProps) {
  return(
    <IconButtonContainer onClick={onClick}>
      {children}
      <Tooltip>{ tooltip }</Tooltip>
    </IconButtonContainer>
  )
}

export default IconButton;