export const API_URLS = {
  myOngoing: (userId) => `/a/bookclubs/my/ongoing?userId=${userId}`,
  myCompleted: (userId) => `/a/bookclubs/my/completed?userId=${userId}`,
  allBookclubs: "/a/bookclubs/search?sortBy=latest",
  memo: (clubId, userId) => `/a/memo/${clubId}/${userId}`,
  finalizeMemo: (clubId, userId) => `/a/memo/${clubId}/${userId}/finalize`,
  bookclubDetail: (clubId, userId) => `/a/bookclubs/${clubId}/detail?userId=${userId}`,
  joinBookclub: (clubId, userId) => `/a/bookclubs/${clubId}/join?userId=${userId}`,
  completedDetail: (clubId) => `/a/bookclubs/${clubId}/completed-detail`,
  bookreport: "/a/reading-notes",

  signup: "/p/user/signup",
  user: "/p/user",
  getUserInfo: (userId) => `/p/user/${userId}`,
  updateUserName: (userId) => `/p/user/${userId}/name`,
  updateUserPassword: (userId) => `/p/user/${userId}/password`,
  chat: "/p/chat",
  mypage: "/p/mypage"
};
