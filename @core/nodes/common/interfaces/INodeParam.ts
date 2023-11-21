// common/interfaces/INodeParam.ts

/**
 * Define the type of a node parameter.
 */

export interface INodeParam {
  type: NodeParamType;
  defaultValue?: unknown;
  description?: string;
  displayOptions?: INodeDisplayOptions;
  options?: INodeParamOption[] | INodeParamOptionGroup[];
  subParams?: INodeParam[];
}

export interface INodeParamOption {
  label: string;
  name: string;
  description?: string;
}

export interface INodeParamOptionGroup {
  name: string;
  options: INodeParamOption[];
}

export interface INodeDisplayOptions {
  show?: {
    [paramName: string]: string[] | boolean;
  };
  hide?: {
    [paramName: string]: string[] | boolean;
  };
}

export enum NodeParamType {
  String = "string",
  Number = "number",
  Boolean = "boolean",
  Json = "json",
  Select = "select",
  MultiSelect = "multiSelect",
  Collection = "collection",
  DateTime = "dateTime",
  Credential = "credential",
}
