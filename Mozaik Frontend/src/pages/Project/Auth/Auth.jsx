import { useState } from "react";
import Signup from "./Signup";
import Login from "./Login";
import { Button } from "@/components/ui/button";
import "./Auth.css";
import { Card } from "@/components/ui/card";

const Auth = () => {
  const [active, setActive] = useState(true);
  return (
    <div className="grid grid-cols-[1fr_2fr] h-screen">
      <section className="login-bg flex flex-col justify-center items-center ">
        <h1>Mosaik</h1>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Soluta
          veritatis distinctio provident, repellat asperiores perspiciatis quis
        </p>
      </section>
      <section className="flex justify-center items-center">
        <div>
          {active ? <Signup /> : <Login />}
          <div className="flex justify-center items-center">
            <span>Already have an account?</span>
            <Button variant="ghost" onClick={() => setActive(!active)}>
              {active ? "Login" : "Signup"}
            </Button>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Auth;
