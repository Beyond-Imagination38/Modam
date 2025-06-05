export const COMMON_API_URL = "http://localhost:8080";

export const API_URLS = {
  user: `${COMMON_API_URL}/user`,
  signup: `${COMMON_API_URL}/user/signup`,
  api: `${COMMON_API_URL}/api`,
  ongoing: `${COMMON_API_URL}/ongoing`,
  chat: `${COMMON_API_URL}/chat`,
  message: `${COMMON_API_URL}/users/message`,
  ws: `${COMMON_API_URL}/users/ws`,
  review: `${COMMON_API_URL}/review`,
  mypage: `${COMMON_API_URL}/mypage`,
  bookreport: `${COMMON_API_URL}/bookreport`,
  bookclubs: `${COMMON_API_URL}/api/bookclubs`,
  myOngoing: (userId) => `${COMMON_API_URL}/api/bookclubs/my/ongoing?userId=${userId}`,
  myCompleted: (userId) => `${COMMON_API_URL}/api/bookclubs/my/completed?userId=${userId}`,
  allBookclubs: `${COMMON_API_URL}/api/bookclubs/search?sortBy=least`,
  saveMemo: (clubId) => `/api/memo/${clubId}`,
  getMemo: (clubId) => `/api/memo/${clubId}`,
};