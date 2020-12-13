export interface ITimesheetstatus {
  notes?: string;
  statuschangedate?: Date;
  statusid: number;
  timesheetid: number;

  statusDescriptiveField?: number;
  timesheetDescriptiveField?: number;
}
