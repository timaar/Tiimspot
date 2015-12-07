'use strict';

angular.module('tiimspotApp')
	.controller('ContactinfoDeleteController', function($scope, $modalInstance, entity, Contactinfo) {

        $scope.contactinfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contactinfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });