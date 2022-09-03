/**
 * @classdesc DictionaryData
 * @property {String} label 标签
 * @property {*} value 标签
 * @property {Object} raw 原始Data
 */
export default class DictData {
  constructor(label, value, raw) {
    this.label = label
    this.value = value
    this.raw = raw
  }
}
