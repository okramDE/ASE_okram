export interface AufgabeDto {
  id?: number;
  titel: string;
  deadline: string;
  prioritaet: 'HIGH' | 'MEDIUM' | 'LOW';
  kategorieId: number;
}
