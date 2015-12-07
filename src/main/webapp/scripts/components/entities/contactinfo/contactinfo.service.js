'use strict';

angular.module('tiimspotApp')
    .factory('Contactinfo', function ($resource, DateUtils) {
        return $resource('api/contactinfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
