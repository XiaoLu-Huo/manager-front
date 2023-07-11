import moment from 'moment';

export const formatDate = (value, formatter = 'YYYY-MM-DD'): string => {
  if (!value) {
    return '';
  }
  const date = new Date(value);
  return moment(date).format(formatter);
};



export const formatTimestamp = (timestamp: number | string, { hour, minute, second } = { hour: 0, minute: 0, second: 0 }): string => {
  if(!timestamp) {
    return '';
  }
  return moment(timestamp).hour(hour).minute(minute).second(second).valueOf().toString();
};





