export const COMMON_API_URL = "http://localhost:8080";

export const API_URLS = {
  user: `${COMMON_API_URL}/user`,
  bookclub: `${COMMON_API_URL}/bookclub`,
  chat: `${COMMON_API_URL}/chat`,
  message: `${COMMON_API_URL}/users/message`,
  ws: `${COMMON_API_URL}/users/ws`,
  review: `${COMMON_API_URL}/review`,
  mypage: `${COMMON_API_URL}/mypage`,
  bookreport: `${COMMON_API_URL}/bookreport`,
  saveMemo: (clubId) => `/api/memo/${clubId}`,
  getMemo: (clubId) => `/api/memo/${clubId}`,
};