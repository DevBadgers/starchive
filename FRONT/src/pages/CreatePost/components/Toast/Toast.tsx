import { Message, ToastWrapper } from "./Toast.style";
import useToast from "./useToast";

function Toast() {
  const {shouldRender, isVisible, message, hideToast} = useToast();

  return shouldRender ? (
    <ToastWrapper $isVisible={isVisible} onClick={hideToast}>
      <Message>{ message }</Message>
    </ToastWrapper>
  ) : null;
}

export default Toast;