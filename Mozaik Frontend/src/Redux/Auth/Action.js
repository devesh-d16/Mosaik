import { API_BASE_URL } from "@/config/api";
import * as actionTypes from "./ActionTypes";
import axios from "axios";

export const register = (userData) => async (dispatch) => {
  dispatch({ type: actionTypes.REGISTER_REQUEST });
  try {
    const { data } = await axios.post(`${API_BASE_URL}/auth/signup`, userData);
    if (data.jwt) {
      localStorage.setItem("jwt", data.jwt);
      dispatch({ type: actionTypes.REGISTER_SUCCESS, payload: data });
    }
    console.log("Register success", data);
  } catch (error) {
    console.log("error :" + error);
    dispatch({
      type: actionTypes.REGISTER_FAILURE,
      data: error.message,
    });
  }
};

export const login = (userData) => async (dispatch) => {
  dispatch({ type: actionTypes.LOGIN_REQUEST });
  try {
    const { data } = await axios.post(`${API_BASE_URL}/auth/signin`, userData);
    if (data.jwt) {
      localStorage.setItem("jwt", data.jwt);
      dispatch({ type: actionTypes.LOGIN_SUCCESS, payload: data });
    }
    console.log("Login success", data);
  } catch (error) {
    console.log(error);
  }
};

export const getUser = () => async (dispatch) => {
  dispatch({ type: actionTypes.GET_USER_REQUEST });
  try {
    const { data } = await axios.get(`${API_BASE_URL}/api/users/profile`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
      },
    });

    dispatch({ type: actionTypes.GET_USER_SUCCESS, payload: data });

    console.log("User success", data);
  } catch (error) {
    console.log(error);
  }
};

export const logout = () => async (dispatch) => {
  dispatch({ type: actionTypes.LOGOUT });
  localStorage.clear();
};
