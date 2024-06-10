import api from "@/config/api";
import * as actionTypes from "./ActionTypes";

export const sendMessage = (messageData) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.SEND_MESSAGE_REQUEST });
    try {
      const response = await api.post("api/messages/send", messageData);
      dispatch({
        type: actionTypes.SEND_MESSAGE_SUCCESS,
        message: response.data,
      });
      console.log("message sent : " + response.data);
      console.log("message sent : " + messageData);
    } catch (error) {
      console.log(error);
      dispatch({
        type: actionTypes.SEND_MESSAGE_FAILURE,
        error: error.message,
      });
    }
  };
};

export const fetchChatByProject = (projectID) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.FETCH_CHAT_BY_PROJECT_REQUEST });
    try {
      const response = await api.get(`/api/projects/${projectID}/chat`);
      console.log("fetch chat", response.data);
      dispatch({
        type: actionTypes.FETCH_CHAT_BY_PROJECT_SUCCESS,
        chat: response.data,
      });
    } catch (error) {
      console.log("error : " + error);
      dispatch({
        type: actionTypes.FETCH_CHAT_BY_PROJECT_FAILURE,
        error: error.message,
      });
    }
  };
};

export const fetchChatMessages = (chatID) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.FETCH_CHAT_MESSAGE_REQUEST });
    console.log(chatID);
    try {
      const response = await api.get(`/api/messages/chat/${chatID}`);
      console.log("fetched message : ", response.data);
      dispatch({
        type: actionTypes.FETCH_CHAT_MESSAGE_SUCCESS,
        chatID,
        messages: response.data,
      });
    } catch (error) {
      console.log("error : " + error);
      dispatch({
        type: actionTypes.FETCH_CHAT_MESSAGE_FAILURE,
        error: error.message,
      });
    }
  };
};
