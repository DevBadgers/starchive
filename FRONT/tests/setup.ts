import { expect, afterEach } from 'vitest';
import { cleanup } from '@testing-library/react';
import matchers from '@testing-library/jest-dom/matchers';

// test 간 DOM 상태를 초기화
afterEach(() => {
  cleanup();
});