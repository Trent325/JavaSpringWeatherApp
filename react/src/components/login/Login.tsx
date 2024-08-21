import React, { useState } from "react";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";
import { useAuth } from "../../contexts/AuthContext";
import { Link, useNavigate } from "react-router-dom";

const Login: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError("");

    function parseJwt(token: string): any {
      const base64Url = token.split('.')[1];
      const base64 = decodeURIComponent(atob(base64Url).replace(/\+/g, ' '));
      return JSON.parse(base64);
    }

    try {
      // Construct the URL with query parameters
      const url = new URL("http://localhost:6969/api/login");
      url.searchParams.append("username", email);
      url.searchParams.append("password", password);

      // Make the request with the parameters in the URL
      const response = await fetch(url.toString(), {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      const token = await response.text();
      console.log("Token:", token);

      // Remove the 'Bearer ' prefix if present
      const cleanToken = token.replace("Bearer ", "");

      // Decode the JWT
      const decodedToken: any = parseJwt(cleanToken);

      console.log("Decoded Token:", decodedToken);
      console.log("token" + decodedToken.roles[0]);

      if (!response.ok) {
        console.log("in here");
        const data = await response.json();
        setError(data.message || "Login failed");
        return;
      }

      login(token); 

      const isAdmin = decodedToken.roles[0] === "ROLE_ADMIN";
      console.log(isAdmin);
      

      if (isAdmin) {
        navigate("/admin-dashboard"); // Redirect to admin dashboard
      } else {
        console.log("in here");
        navigate("/Region"); // Redirect to user dashboard
      }
    } catch (error) {
      setError("An error occurred");
    }
  };

  return (
    <Container className="bg-white">
      <Row className="justify-content-md-center">
        <Col md={6}>
          <h2 className="text-center my-4">Login</h2>
          {error && <Alert variant="danger">{error}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formBasicEmail" className="mb-3">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </Form.Group>

            <Form.Group controlId="formBasicPassword" className="mb-3">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </Form.Group>

            <Button variant="primary" type="submit" className="w-100">
              Login
            </Button>

            <div className="text-center mt-3">
              <Link to="/register">Create an account</Link>
            </div>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default Login;
