import { useState } from "react";
import { EyeIcon, Fieldset, Input, Label, PasswordInputContainer } from "../Auth.style";
import hideIcon from "@_assets/icons/hide-icon.svg";
import showIcon from "@_assets/icons/show-icon.svg";

function PasswordInput() {
  const [showPassword, setShowPassword] = useState(false);

  return (
  <Fieldset>
    <Label htmlFor="password">비밀번호</Label>
    <PasswordInputContainer>
      <Input id="password" type={showPassword ? "text" : "password"} placeholder="비밀번호" />
      <EyeIcon
        src={showPassword ? showIcon : hideIcon}
        alt={showPassword ? "Hide password" : "Show password"}
        onClick={() => setShowPassword(prev => !prev)}
      />
    </PasswordInputContainer>
  </Fieldset>
  )
}

export default PasswordInput;