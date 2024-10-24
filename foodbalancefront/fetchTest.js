document.getElementById('fetchDataBtn').addEventListener('click', fetchData);

async function fetchData() {
    try {
        const response = await fetch('http://localhost:8000/api/index.php', {
            method: 'GET',
            mode: 'no-cors',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Verifica si la respuesta fue exitosa
        // if (!response.ok) {
        //     throw new Error(`HTTP error! status: ${response.status}`);
        // }

        const data = await response.json();
        console.log(data); // Muestra los datos en la consola

    } catch (error) {
        console.error('Error al obtener los datos:', error);
    }
}
