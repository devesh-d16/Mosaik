import api from "@/config/api";
import * as actionTypes from "./ActionTypes";

export const createComment = (commentData) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.CREATE_COMMENT_REQUEST });
    try {
      const response = await api.post(`/api/comments`, commentData);
      console.log("comment created : ", response.data);
      dispatch({
        type: actionTypes.CREATE_COMMENT_SUCCESS,
        comment: response.data,
      });
    } catch (error) {
      console.log("error : " + error);
      dispatch({
        type: actionTypes.CREATE_COMMENT_FAILURE,
        error: error.message,
      });
    }
  };
};

export const deleteComment = (commentID) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.DELETE_COMMENT_REQUEST });
    try {
      await api.delete(`/api/comments/${commentID}`);
      dispatch({ type: actionTypes.DELETE_COMMENT_SUCCESS, commentID });
    } catch (error) {
      console.log("error :", error);
      dispatch({
        type: actionTypes.DELETE_COMMENT_FAILURE,
        error: error.message,
      });
    }
  };
};
export const fetchComments = (issueID) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.FETCH_COMMENTS_REQUEST });
    try {
      const response = await api.get(`/api/comments/${issueID}`);
      dispatch({
        type: actionTypes.FETCH_COMMENTS_SUCCESS,
        comments: response.data,
      });
    } catch (error) {
      console.log("error :", error);
      dispatch({
        type: actionTypes.FETCH_COMMENTS_FAILURE,
        error: error.message,
      });
    }
  };
};
