import { useState } from "react";
import hideIcon from "@_assets/icons/hide-icon.svg";
import showIcon from "@_assets/icons/show-icon.svg";
import { PasswordVisibilityToggleButton, FieldContainer, Guidance, Input, Label, PasswordInputContainer } from "./Field.style";

interface FieldProps {
  id: string;
  label: string;
  placeholder: string;
  type: "text" | "password";
  showGuidance?: boolean;
}

function Field({ id, label, placeholder, type, showGuidance = false } : FieldProps) {
  // TODO: value는 부모 컴포넌트에서 value로 받도록 수정할 예정
  const [value, setValue] = useState('');

  const [showPassword, setShowPassword] = useState(false);
  const handleTogglePasswordVisibility = () => setShowPassword((prev) => !prev);

  // TODO: 회의에서 비밀번호 정책을 정한 후 수정할 예정
  const PASSWORD_REGEX = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,15}$/;
  const isValidPassword = (password: string) => PASSWORD_REGEX.test(password);

  return (
    <FieldContainer>
      <Label htmlFor={id}>{ label }</Label>
      {type === "password" ? (
        <>
          <PasswordInputContainer>
            <Input
              id={id}
              value={value}
              type={showPassword ? "text" : "password"}
              placeholder={placeholder}
              onChange={(e) => setValue(e.target.value)}
            />
            <PasswordVisibilityToggleButton
              type="button"
              onClick={handleTogglePasswordVisibility}
              aria-label={showPassword ? "비밀번호 숨기기" : "비밀번호 보기"}
            >
              <img src={showPassword ? showIcon : hideIcon} alt="" />
            </PasswordVisibilityToggleButton>
          </PasswordInputContainer>
          {showGuidance && !isValidPassword(value) &&
            <Guidance><p>영문, 숫자를 포함한 6~15자를 입력하세요</p></Guidance>
          }
        </>
      ) : (
        <Input
          id={id}
          value={value}
          type={type}
          placeholder={placeholder}
          onChange={(e) => setValue(e.target.value)}
        />
      )}
    </FieldContainer>
  )
}

export default Field;