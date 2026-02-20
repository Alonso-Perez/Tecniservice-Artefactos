//Al ser estrictos con la configuración realizada en mi DTO de Backend, tenemos que seguir lo siguiente:

export interface Equipo{
  id: number;
  tipo: string;
  marca: string;
  modelo: string;
  serie: string;
}

export interface Orden {
  id: number;
  nombreCliente: string;
  nombreTecnico: string;
  descripcionProblema: string;
  estado: string; // PENDIENTE, EN_PROCESO, etc.
  fechaCreacion: string; //En Java es LocalDateTime, pero en TS llega como String ISO

  //Con en mi Java tengo una lista de equipos, aquí también se define
  equipos: Equipo[];

}
