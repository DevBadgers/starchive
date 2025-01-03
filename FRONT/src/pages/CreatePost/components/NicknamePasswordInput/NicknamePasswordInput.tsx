import useCreatePost from '../../hooks/useCreatePost';
import { InputContainer, StyledInput } from './NicknamePasswordInput.style';

function NicknamePasswordInput() {
  const { post, handlePostChange } = useCreatePost();

  return (
    <InputContainer>
      <StyledInput
        placeholder="닉네임을 입력하세요"
        value={post.author}
        onChange={(e) => handlePostChange('author', e.target.value)}
        required
      />
      <StyledInput
        type="password"
        placeholder="비밀번호를 입력하세요"
        value={post.password}
        onChange={(e) => handlePostChange('password',e.target.value)}
        required
      />
    </InputContainer>
  );
}

export default NicknamePasswordInput;