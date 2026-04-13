import React, { useEffect, useState } from "react";

function PeopleList() {
  const [people, setPeople] = useState([]);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  // Cargar lista de personas
  const loadPeople = async () => {
    try {
      const response = await fetch("http://localhost:9090/api/people");
      const data = await response.json();

      // Si viene con _embedded.people (Spring Data REST)
      if (data._embedded && data._embedded.people) {
        setPeople(data._embedded.people);
      } else {
        // Si viene como array plano
        setPeople(data);
      }
    } catch (error) {
      console.error("Error cargando personas:", error);
    }
  };

  useEffect(() => {
    loadPeople();
  }, []);

  // Crear persona
  const addPerson = (e) => {
    e.preventDefault();
    const newPerson = { firstName, lastName };

    fetch("http://localhost:9090/api/people", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newPerson),
    })
      .then(() => {
        loadPeople(); // refrescar lista después de guardar
        setFirstName("");
        setLastName("");
      })
      .catch(error => console.error("Error:", error));
  };


  return (
     <div>
       <h1>Lista de Personas</h1>
       <button onClick={loadPeople}>Actualizar Lista</button>
       <ul>
         {Array.isArray(people) && people
           .filter(p => p.firstName || p.lastName) // ignorar vacíos
           .map((person, index) => (
             <li key={index}>
               {person.firstName} {person.lastName}
             </li>
           ))}
       </ul>

       <h2>Agregar Persona</h2>
       <form onSubmit={addPerson}>
         <input
           type="text"
           placeholder="Nombre"
           value={firstName}
           onChange={(e) => setFirstName(e.target.value)}
         />
         <input
           type="text"
           placeholder="Apellido"
           value={lastName}
           onChange={(e) => setLastName(e.target.value)}
         />
         <button type="submit">Agregar</button>
       </form>
     </div>
   );
 }

export default PeopleList;
