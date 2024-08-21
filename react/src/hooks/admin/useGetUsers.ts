import { useQuery } from 'react-query';

export const useAllUsers = () => {
  const token = localStorage.getItem('token') || '';

  return useQuery(
    'allUsers',
    async () => {
      console.log(token)
      const response = await fetch('http://localhost:8080/api/admin/users', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${token}`, // Include token in the header
        },
      });

      if (!response.ok) {
        throw new Error('Failed to fetch users');
      }
      return response.json();
    }
    
  );
};
